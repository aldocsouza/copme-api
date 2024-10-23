package br.gov.pa.iasep.opmepro_api.controller;

import br.gov.pa.iasep.opmepro_api.model.dtos.UserAgentDTOs.ResponseAgentDTO;
import br.gov.pa.iasep.opmepro_api.services.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService){
        this.agentService = agentService;
    }

    public ResponseEntity<List<ResponseAgentDTO>> getAllAgents(){
        return ResponseEntity.status(HttpStatus.OK).body(agentService.getAllAgents());
    }

}