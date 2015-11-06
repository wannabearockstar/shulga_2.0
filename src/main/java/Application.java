import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by wannabe on 06.11.15.
 */
@SpringBootApplication
@ComponentScan("web")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
