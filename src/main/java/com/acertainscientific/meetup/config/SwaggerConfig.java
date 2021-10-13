package com.acertainscientific.meetup.config;


import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


@EnableOpenApi
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        //返回文档摘要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
                .paths(PathSelectors.any())
                .build();

    }

    //生成接口信息，包括标题、联系人等
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MeetUp")
                .description("MeetUp")
                .contact(new Contact("ACertainScientific", "https://github.com/ACertainScientific/MeetUp", ""))
                .version("1.0")
                .build();
    }

//    //生成全局通用参数
//    private List<RequestParameter> getGlobalRequestParameters() {
//        List<RequestParameter> parameters = new ArrayList<>();
//        parameters.add(new RequestParameterBuilder()
//                .name("appid")
//                .description("平台id")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                .required(false)
//                .build());
//        parameters.add(new RequestParameterBuilder()
//                .name("udid")
//                .description("设备的唯一id")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                .required(false)
//                .build());
//        parameters.add(new RequestParameterBuilder()
//                .name("version")
//                .description("客户端的版本号")
//                .required(true)
//                .in(ParameterType.QUERY)
//                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                .required(false)
//                .build());
//        return parameters;
//    }
//
//    //生成通用响应信息
//    private List<Response> getGlobalResonseMessage() {
//        List<Response> responseList = new ArrayList<>();
//        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
//        return responseList;
//    }
}

