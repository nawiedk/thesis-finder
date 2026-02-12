package com.devsxplore.thesis.profiles.domain.model;

import com.devsxplore.thesis.ThesisApplication;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IAttribute;

import java.net.URL;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.adhereToPlantUmlDiagram;

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

    @ArchTest
    ArchRule controllerClassNameHasToEndWithController = classes()
            .that()
            .resideInAPackage("..in.web")
            .should()
            .haveSimpleNameEndingWith("Controller")
            .andShould()
            .beAnnotatedWith(Controller.class);

    @ArchTest
    ArchRule servicesShouldEndWithService = classes()
            .that()
            .resideInAnyPackage("..service..")
            .should()
            .haveSimpleNameEndingWith("Service")
            .andShould()
            .beAnnotatedWith(Service.class);

    @ArchTest
    ArchRule noSpringAnnotationInDomainFields = fields()
            .that()
            .areDeclaredInClassesThat()
            .resideInAPackage("..domain.model")
            .should()
            .notBeAnnotatedWith(Id.class)
            .andShould()
            .notBeAnnotatedWith(MappedCollection.class);

    @ArchTest
    ArchRule noSpringAnnotationInDomainModel = classes()
            .that()
            .resideInAPackage("..domain.model")
            .should()
            .notBeAnnotatedWith(Component.class)
            .andShould()
            .notBeAnnotatedWith(Service.class)
            .andShould()
            .notBeAnnotatedWith(Controller.class);

    @ArchTest
    ArchRule noFieldInjectionIsUsed = fields()
            .that()
            .areDeclaredInClassesThat()
            .resideInAnyPackage("..profiles..")
            .should()
            .notBeAnnotatedWith(Autowired.class);

    @ArchTest
    ArchRule serviceDoesNotDirectlyAccessCrud = classes()
            .that()
            .resideInAPackage("..service..")
            .should()
            .onlyAccessClassesThat()
            .areNotAssignableTo(CrudRepository.class);


    URL uml = getClass().getResource("/architecture.puml");
    @ArchTest
    ArchRule architectureConformsToUMLDiagram = classes().should(adhereToPlantUmlDiagram
            (uml, PlantUmlArchCondition.Configuration.consideringOnlyDependenciesInDiagram()));


}