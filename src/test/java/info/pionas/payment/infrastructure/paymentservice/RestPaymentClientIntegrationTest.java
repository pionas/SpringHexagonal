//package info.pionas.payment.infrastructure.paymentservice;
//
//import com.smalaca.rentalapplication.paymentservice.payment.PaymentContract;
//import com.smalaca.rentalapplication.paymentservice.payment.PaymentRequest;
//import com.smalaca.rentalapplication.paymentservice.payment.PaymentScenario;
//import info.pionas.payment.domain.payment.PaymentService;
//import info.pionas.payment.domain.payment.PaymentStatus;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static info.pionas.payment.domain.payment.PaymentStatus.NOT_ENOUGH_MONEY;
//import static info.pionas.payment.domain.payment.PaymentStatus.SUCCESS;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Tag("IntegrationTest")
//class RestPaymentClientIntegrationTest {
//    private final PaymentContract contract = new PaymentContract();
//    @Autowired
//    private PaymentService paymentService;
//
//    @Test
//    void shouldPaySuccessfully() {
//        PaymentScenario scenario = contract.successfulPayment();
//        PaymentRequest request = scenario.getRequest();
//        PaymentStatus actual = paymentService.transfer(request.getSenderId(), request.getRecipientId(), request.getAmount());
//        assertThat(actual).isEqualTo(SUCCESS);
//    }
//
//    @Test
//    void shouldRecognizeThereIsNotEnoughMoney() {
//        PaymentScenario scenario = contract.notEnoughMoney();
//        PaymentRequest request = scenario.getRequest();
//        PaymentStatus actual = paymentService.transfer(request.getSenderId(), request.getRecipientId(), request.getAmount());
//        assertThat(actual).isEqualTo(NOT_ENOUGH_MONEY);
//    }
//}