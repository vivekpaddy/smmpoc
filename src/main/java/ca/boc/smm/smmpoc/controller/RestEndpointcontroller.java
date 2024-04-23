package ca.boc.smm.smmpoc.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/hello")
public class RestEndpointcontroller {

  @GetMapping
  public List<Map<String, Object>> helloWorld()
  {

    return List.of(Map.of("id",1,"name","hello"),
        Map.of("id",2,"name","world"));
  }

}
