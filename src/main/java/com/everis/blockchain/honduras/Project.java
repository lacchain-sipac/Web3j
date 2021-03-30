package com.everis.blockchain.honduras;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {

	private String flow;
    private String roles;
    private Boolean finalized;
    private Boolean initialized;
    private String currentStep;
    private String creator;

    public Project(String _flow, String _roles, Boolean _finalized, Boolean _initialized, String _currentStep, String _creator) {
        flow = _flow;
        roles = _roles;
        finalized = _finalized;
        initialized = _initialized;
        currentStep = _currentStep;
        creator = _creator;
    }
}
