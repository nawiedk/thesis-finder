package com.devsxplore.thesis.supervisors.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValueObjectBranchTest {

    @Test
    void fieldTag_should_reject_invalid_inputs() {
        assertThatThrownBy(() -> new FieldTag(""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new FieldTag("a".repeat(51)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void studentId_should_reject_non_positive_values() {
        assertThatThrownBy(() -> new SupervisorId(0L))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new SupervisorId(-5L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}