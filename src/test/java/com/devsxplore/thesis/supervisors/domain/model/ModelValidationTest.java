package com.devsxplore.thesis.supervisors.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ModelValidationTest {

    @Test
    void contact_ShouldNormalizeOfficeAndPhone() {
        Contact contact = Contact.contactFromPrimitive("test@hhu.de", "  Room A  ", "  123  ");

        assertThat(contact.office()).isEqualTo("Room A");
        assertThat(contact.phone()).isEqualTo("123");
    }

    @Test
    void topicId_ShouldRejectNegativeValues() {
        assertThatThrownBy(() -> new TopicId(-1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void academicTitle_FindByString_ShouldReturnNoneForInvalidInput() {
        assertThat(AcademicTitle.findByString("Invalid")).isEqualTo(AcademicTitle.NONE);
        assertThat(AcademicTitle.findByString(null)).isEqualTo(AcademicTitle.NONE);
    }
}