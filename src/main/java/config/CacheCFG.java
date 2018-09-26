package config;

import cashes.CacheTop;
import cashes.CasheBottom;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class CacheCFG {

    @Bean
    @Description("Кэш верхнего уровня")
    CacheTop CacheTop(CasheBottom casheBottom) {
        return new CacheTop();
    }


    @Bean
    @Description("Кэш нижнего уровня")
    CasheBottom CasheBottom() {
        return new CasheBottom();
    }

}
