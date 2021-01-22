package info.pionas.rental.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

class PackageStructureTest {
    @Test
    void domainSHouldTalkOnlyWithDomain() {
        JavaClasses javaClasses = RentalApplicationClasses.get();

        ArchRuleDefinition.classes()
                .that().resideInAPackage("..domain..")
                .should().onlyAccessClassesThat().resideInAnyPackage("..domain..", "java..")
                .check(javaClasses);
        ;
    }
}
