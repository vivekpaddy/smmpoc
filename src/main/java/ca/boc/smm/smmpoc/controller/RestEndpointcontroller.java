package ca.boc.smm.smmpoc.controller;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/hello")
public class RestEndpointcontroller {

  @GetMapping
  public List<String> helloWorld()
  {
    return List.of("hello","world");
  }

}
