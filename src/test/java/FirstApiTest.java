import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class FirstApiTest {
    //Basic Test
    @Test()
    public void testGet() {
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("http://httpbin.org/get?a=1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("headers.Host", equalTo("httpbin.org"))
                        .body("args.a", equalTo("1"))
                        .extract()
                        .response();
        response1.getBody().print();
    }
    @Test()
    public void testPOST() {

        Map<String, String> data = new HashMap<>();
        data.put("orderId", "2");
        given()
//                .contentType("application/json")
                .body(data)
                .when()
                .post("http://httpbin.org/post").then()
                .statusCode(200)

                .body("json.orderId", equalTo("2"));
    }
//Test weather
    @Test()
    public void testWeatherLondon_GET() {
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=3f100ad70d16bc8fc6b5cbd43eacdb08")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("sys.country", equalTo("GB"))
                        .body("weather.main[0]", equalTo("Clouds"))
//                        .body("weather.id[0]", equalTo("804"))
//                        .body("id", equalTo("2643743"))
                        .extract()
                        .response();
        response1.getBody().print();
    }
    @Test()
    public void testWeatherLondon2_GET() {
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=3f100ad70d16bc8fc6b5cbd43eacdb08")
                        .then()
                        .log().all()
                        .statusCode(200)
//                        .body("sys.country", equalTo("GB"))
                        .body("weather.id[0]", equalTo(804))
                        .body("weather.icon[0]", equalTo("02n"))
                        .body("id", equalTo(2643743))
                        .extract()
                        .response();
        response1.getBody().print();
    }

    //Privat Bank
    @Test()
    public void testPrivat_id1_GET(){
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("ccy[0]", equalTo("BTC"))
                         .body("sale[0]", greaterThanOrEqualTo("11000"))
                        .extract()
                        .response();
        response1.getBody().print();
    }
    @Test()
    public void testPrivat_id5_GET(){
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("ccy[1]", equalTo("EUR"))
                        .body("sale[2]", equalTo("0.38000"))
                        .extract()
                        .response();
        response1.getBody().print();
    }
    //All Examples https://reqres.in/

    //List Of Users
    @Test()
    public void listOfUsers_GET(){
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("https://reqres.in/api/users?page=2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("page", equalTo(2))
                        .body("total", equalTo(12))
                        .body("total_pages", greaterThanOrEqualTo(1))
                        .body("data.id[3]", equalTo(10))
                        .body("data.email[3]", equalTo("byron.fields@reqres.in"))
                        .body("data.first_name[3]", equalTo("Byron"))
                        .body("data.last_name[3]", equalTo("Fields"))
                        .body("data.avatar[3]", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/russoedu/128.jpg"))
                        .body("ad.url", equalTo("http://statuscode.org/"))
                        .extract()
                        .response();
        response1.getBody().print();
    }
    //User not found +
    @Test()
    public void userError_GET(){
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("https://reqres.in//api/users/23")
                        .then()
                        .log().all()
                        .statusCode(404)
                        .extract()
                        .response();
        response1.getBody().print();
    }
//Create User PostRequest

//@Test()
//public void createUser_POST() {
//
//    Map<String, String> data = new HashMap<>();
//    data.put("name", "morpheus");
//    data.put("job", "leader");
//    given()
////                .contentType("application/json")
//            .body(data)
//            .when()
//            .post("https://reqres.in/api/users").then()
//            .statusCode(201)
//            .body("name", equalTo("morpheus"))
//            .body("job", equalTo("leader"))
//            .body("id", equalTo("885"));
//}


    }







