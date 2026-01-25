package com.devsxplore.thesis.profiles.domain.model;

import com.devsxplore.thesis.ThesisApplication;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

//TODO: Gradle Submodules und Archunit Tests!!!!!

@AnalyzeClasses(packagesOf = ThesisApplication.class, importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

    @ArchTest
    ArchRule onionArchitecture = onionArchitecture()
            .domainModels("..domain.model..")
            .applicationServices("..application..")
            .domainServices("..application..")
            .adapter("web","..in.web..")
            .adapter("persistence","..out.persistence..");


    //FIXME: funktioniert nicht korrekt galigrü
    @ArchTest
    ArchRule serviceCanOnlyUsePort = classes()
            .that()
            .areInterfaces()
            .and()
            .resideOutsideOfPackage("..repository")
            .should()
            .notBeAssignableFrom(CrudRepository.class);

//    @ArchTest
//    ArchRule domainShouldNotHaveADependencyFromOuterLayers = classes()
//            .that()
//            .resideInAnyPackage("..")
}