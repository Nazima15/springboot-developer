import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JUnitQuiz {

    @Test
    public void juniQuiz1(){
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();

        // name1 name2 같은지
        assertThat(name1).isEqualTo(name2);

        // name1 name3 다른지
        assertThat(name1).isNotEqualTo(name3);
    }
    @Test
    public void junitQuiz2() {
        int number1 = 15;
        int number2 = 0;
        int number3 = -5;


        // ❶ number1은 양수인지 확인
        assertThat(number1).isPositive();


        // ❷ number2은 0인지 확인
        assertThat(number2).isZero();


        // ❸ number3은 음수인지 확인
        assertThat(number3).isNegative();


        // ❹ number1은 number2보다 큰지 확인
        assertThat(number1).isGreaterThan(number2);


        // ❺ number3은 number2보다 작은지 확인
        assertThat(number3).isLessThan(number2);
    }

}