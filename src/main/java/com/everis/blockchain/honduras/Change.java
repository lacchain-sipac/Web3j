package com.everis.blockchain.honduras;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Change {

    private String accreditDocument;
    private String revokeDocument;
    private String accreditComment;
    private String user;
    private String roleUser;
    private Boolean isFinal;
    private BigInteger timestamp;

    public Change(String _accreditDocument, String _revokeDocument, String _accreditComment, String _user, String _roleUser, Boolean _isFinal, BigInteger _timestamp) {
        accreditDocument = _accreditDocument;
        revokeDocument = _revokeDocument;
        accreditComment = _accreditComment;
        user = _user;
        roleUser = _roleUser;
        isFinal = _isFinal;
        timestamp = _timestamp;
    }
}