package com.everis.blockchain.honduras.impl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authorization {

	private Boolean isAuthorized;
    private Boolean isProjectSpecific;

    public Authorization(Boolean _isAuthorized, Boolean _isProjectSpecific) {
        isAuthorized = _isAuthorized;
        isProjectSpecific = _isProjectSpecific;
    }
}
