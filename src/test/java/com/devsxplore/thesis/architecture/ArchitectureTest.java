package com.devsxplore.thesis.architecture;

import com.devsxplore.thesis.ThesisApplication;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.repository.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = ThesisApplication.class, importOptions = { ImportOption.DoNotIncludeTests.class })
public class ArchitectureTest {

	@ArchTest
	static final ArchRule onion_architecture_is_respected = onionArchitecture().withOptionalLayers(true)
		.domainModels("..domain.model..")
		.domainServices("..domain.service..")
		.applicationServices("..application..")
		.adapter("web", "..adapter.in.web..")
		.adapter("persistence", "..adapter.out.persistence..")
		.adapter("security", "..adapter.in.security..")
		.ignoreDependency("..adapter.in.web..", "..adapter.in.security..");

	@ArchTest
	static final ArchRule ports_should_not_depend_on_spring_data = classes().that()
		.resideInAPackage("..application.port..")
		.should()
		.notBeAssignableTo(Repository.class)
		.because("Ports in der Application-Schicht sollten Framework-agnostisch sein.");

	@ArchTest
	static final ArchRule domain_should_not_depend_on_application = noClasses().that()
		.resideInAPackage("..domain..")
		.should()
		.dependOnClassesThat()
		.resideInAPackage("..application..");

}