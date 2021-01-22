package info.pionas.rental.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class PackageStructureTest {
    @Test
    void domainSHouldTalkOnlyWithDomain() {
        JavaClasses javaClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("info.pionas.rental");

        ArchRuleDefinition.classes()
                .that().resideInAPackage("..domain..")
                .should().onlyAccessClassesThat().resideInAnyPackage("..domain..", "java..")
                .check(javaClasses);
        ;
    }
}
