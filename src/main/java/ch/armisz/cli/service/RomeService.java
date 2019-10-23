package ch.armisz.cli.service;

import org.springframework.stereotype.Service;

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
    return true;
  }

  public void parameters(RomeFilter filter) {
    log.info("parameters, filter={}", filter);
  }

  public boolean isParametersReady() {
    return true;
  }

  public void configure(RomeFilter filter) {
    log.info("configure, filter={}", filter);
  }

  public void deploy(RomeFilter filter, boolean ignoreFetch, boolean ignoreConfigure) {
    log.info("deploy, filter={}, ignoreFetch={}, ignoreConfigure={}",filter, ignoreFetch, ignoreConfigure);
  }

  public void images(RomeFilter filter) {
    log.info("images, filter={}", filter);
  }

  public boolean isImagesReady() {
    return true;
  }
}