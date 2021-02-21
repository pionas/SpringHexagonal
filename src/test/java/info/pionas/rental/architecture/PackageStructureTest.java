package info.pionas.rental.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@Tag("ArchitectureTest")
class PackageStructureTest {
    private static final String DOMAIN = "..domain..";
    private static final String APPLICATION = "..application..";
    private static final String JAVA = "java..";
    private static final String APACHE_COMMONS = "org.apache.commons.lang3..";
    private static final String GOOGLE_COMMONS = "com.google.common..";
    private static final String SPRING_SECURITY = "org.springframework.security..";
    private static final String TENANT_COMMONS = "info.pionas.common..";
    private static final String QUERY = "..query..";
    private static final String INFRASTRUCTURE = "..infrastructure..";
    private static final String HATEOS = "org.springframework.hateoas..";

    private final JavaClasses classes = RentalApplicationClasses.get();

    @Test
    void domainShouldTalkOnlyWithDomain() {
        classes().that().resideInAPackage(DOMAIN)
                .should().onlyAccessClassesThat().resideInAnyPackage(DOMAIN, JAVA, APACHE_COMMONS, GOOGLE_COMMONS, SPRING_SECURITY, TENANT_COMMONS)
                .check(classes);
    }

    @Test
    void applicationShouldTalkOnlyWithApplicationAndDomain() {
        classes().that().resideInAPackage(APPLICATION)
                .should().onlyAccessClassesThat().resideInAnyPackage(APPLICATION, DOMAIN, JAVA, SPRING_SECURITY)
                .check(classes);
    }

    @Test
    void queryShouldTalkOnlyWithQuery() {
        classes().that().resideInAPackage(QUERY)
                .should().onlyAccessClassesThat().resideInAnyPackage(QUERY, JAVA, HATEOS)
                .check(classes);
    }

    @Test
    void infrastructureShouldNotTalkWithDomain() {
        classes().that().resideInAPackage(INFRASTRUCTURE)
                .should().onlyAccessClassesThat().resideOutsideOfPackage(DOMAIN)
                .orShould().haveSimpleNameContaining("Repository")
                .check(classes);
    }
}