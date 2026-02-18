package com.devsxplore.thesis.supervisors.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

	@ParameterizedTest
	@ValueSource(strings = { "test@hhu.de", "user@uni-duesseldorf.de", "doc@med.uni-duesseldorf.de" })
	void should_accept_valid_domains(String validEmail) {
		Email email = new Email(validEmail);
		assertThat(email.email()).isEqualTo(validEmail.toLowerCase());
	}

	@ParameterizedTest
	@ValueSource(strings = { "test@gmail.com", "user@outlook.de", "hhu.de" })
	void should_reject_invalid_domains(String invalidEmail) {
		assertThatThrownBy(() -> new Email(invalidEmail)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void should_reject_null_or_blank() {
		assertThatThrownBy(() -> new Email(null)).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Email("")).isInstanceOf(IllegalArgumentException.class);
	}

}