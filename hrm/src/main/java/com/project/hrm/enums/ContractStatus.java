package com.project.hrm.enums;

public enum ContractStatus {
    DRAFT, // TODO: The contract is being initiated, not signed or officially approved.
    SIGNED, // TODO: Signed but no effective date yet.
    ACTIVE,  // TODO: Effective
    EXPIRED, // TODO: Contract expired
    TERMINATED, // TODO: Termination before term
    CANCELLED, // TODO: Contract terminated before taking effect
    SUSPENDED // TODO: Suspend the effect simultaneously for a period of time
}
