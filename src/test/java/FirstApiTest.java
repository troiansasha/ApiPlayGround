import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
                        .body("weather.id[0]", equalTo(801))
                        .body("weather.icon[0]", equalTo("02d"))
                        .body("id", equalTo(2643743))
                        .extract()
                        .response();
        response1.getBody().print();
    }

    //Privat Bank
    @Test()
    public void testPrivat_id1_GET() {
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("ccy[0]", equalTo("BTC"))
                        .body("sale[0]", greaterThanOrEqualTo("10000"))
                        .extract()
                        .response();
        response1.getBody().print();
    }

    @Test()
    public void testPrivat_id5_GET() {
        Response response1 =
                given()
                        .log().all()
                        .when()
                        .get("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("ccy[1]", equalTo("EUR"))
                        .body("sale[2]", equalTo("0.37700"))
                        .extract()
                        .response();
        response1.getBody().print();
    }
    //All Examples https://reqres.in/

    //List Of Users
    @Test()
    public void listOfUsers_GET() {
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
    public void userError_GET() {
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

    @Test
    public void createUser_POST () {


        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "leader");

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post("https://reqres.in/api/users");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);

        String successName = response.jsonPath().get("name");
        Assert.assertEquals(successName, "morpheus");
        String successJob = response.jsonPath().get("job");
        Assert.assertEquals(successJob, "leader");
        System.out.println(response.getBody().asString());

    }

    //Update user Put Request
    @Test
    public void updateUser_PUT () {


        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "zion resident");

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.put("https://reqres.in/api/users/2");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        String successName = response.jsonPath().get("name");
        Assert.assertEquals(successName, "morpheus");
        String successJob = response.jsonPath().get("job");
        Assert.assertEquals(successJob, "zion resident");
        System.out.println(response.getBody().asString());

    }
    //Delete user Put Request
    @Test
    public void deleteUser_PUT () {

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        Response response = request.delete("https://reqres.in/api/users/2");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 204);

    }
    //Register user Post Request
        @Test
        public void registerUser_POST () {


            JSONObject requestBody = new JSONObject();
            requestBody.put("email", "eve.holt@reqres.in");
            requestBody.put("password", "pistol");

            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            request.body(requestBody.toString());
            Response response = request.post("https://reqres.in/api/register");

            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 200);

            int successId = response.jsonPath().get("id");
            Assert.assertEquals(successId, 4);
            String successToken = response.jsonPath().get("token");
            Assert.assertEquals(successToken, "QpwL5tke4Pnpja7X4");
            System.out.println(response.getBody().asString());

        }
    @Test
    public void registerUserUnsuccesfull_POST () {


        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");
        //Empty Password

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post("https://reqres.in/api/register");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);

        String successToken = response.jsonPath().get("error");
        Assert.assertEquals(successToken, "Missing password");
        System.out.println(response.getBody().asString());

    }
    @Test
    public void LoginSuccesfull_POST () {


        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post("https://reqres.in/api/login");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        String successToken = response.jsonPath().get("token");
        Assert.assertEquals(successToken, "QpwL5tke4Pnpja7X4");
        System.out.println(response.getBody().asString());

    }

    }








