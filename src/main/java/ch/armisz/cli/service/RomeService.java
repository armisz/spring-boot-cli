package ch.armisz.cli.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * All roads lead to Rome.
 */
@Service
@Slf4j
public class RomeService {

    public void fetch() {
        log.info("fetch");
    }

    public boolean isFetchReady() {
        return Boolean.parseBoolean(getProperty("isFetchReady"));
    }

    public void parameters(RomeFilter filter) {
        log.info("parameters, filter={}", filter);
    }

    public boolean isParametersReady() {
        return Boolean.parseBoolean(getProperty("isParametersReady"));
    }

    public void configure(RomeFilter filter) {
        log.info("configure, filter={}", filter);
    }

    public void deploy(RomeFilter filter, boolean ignoreFetch, boolean ignoreConfigure) {
        log.info("deploy, filter={}, ignoreFetch={}, ignoreConfigure={}", filter, ignoreFetch, ignoreConfigure);
    }

    public void images(RomeFilter filter) {
        log.info("images, filter={}", filter);
    }

    public boolean isImagesReady() {
        return Boolean.parseBoolean(getProperty("isImagesReady"));
    }

    private String getProperty(String key) {
        try (InputStream stream = new FileInputStream("D:\\dev\\repo\\spring-boot-cli\\src\\main\\resources\\application.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}