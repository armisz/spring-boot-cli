package ch.armisz.cli.service.internal;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class RuleService {

    @Autowired
    private RulesEngine rulesEngine;
    @Autowired
    private Properties parameters;

    @SuppressWarnings("unchecked")
    public Map<String, Object> applyRules(Map<String, Object> yaml) {
        Map<String, Object> result = new LinkedHashMap<>(yaml.size());

        yaml.forEach((key, value) -> {
            if ("i8r-rule".equals(key)) {
                applyRule((Map<String, Object>) value, result);

            } else if ("i8r-rules".equals(key)) {
                for (Object elm : (List<Object>) value) {
                    applyRule((Map<String, Object>) elm, result);
                }

            } else if (value != null) {
                // traverse map
                if (Map.class.isAssignableFrom(value.getClass())) {
                    Map<String, Object> applied = applyRules((Map<String, Object>) value);
                    if (!applied.isEmpty()) {
                        result.put(key, applied);
                    }
                }
                // traverse list
                else if (List.class.isAssignableFrom(value.getClass())) {
                    List<Object> list = new ArrayList<>(((List<Object>) value).size());
                    for (Object elm : (List<Object>) value) {
                        if (Map.class.isAssignableFrom(elm.getClass())) {
                            list.add(applyRules((Map<String, Object>) elm));
                        } else {
                            list.add(elm);
                        }
                    }
                    result.put(key, list);
                }
                // default
                else {
                    result.put(key, value);
                }
            } else {
                result.put(key, null);
            }
        });
        return result;
    }

    @SuppressWarnings("unchecked")
    private void applyRule(Map<String, Object> ruleDescriptor, Map<String, Object> context) {
        Map<String, String> actionDescription = (Map<String, String>) ruleDescriptor.get("action");
        String key = actionDescription.get("key");
        String value = parameters.getProperty(actionDescription.get("value"));

        Rule rule = new MVELRule()
            .when((String) ruleDescriptor.get("condition"))
            .then(String.format("ctx.put(\"%s\",\"%s\")", key, value));
        Rules rules = new Rules(rule);

        Facts facts = new Facts();
        facts.put("ctx", context);
        parameters.forEach((p, v) -> facts.put((String) p, v));

        rulesEngine.fire(rules, facts);
    }
}
