import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTests {

    @BeforeAll
    static void setup() {
        //открываем браузер в максимальном разрешении
        Configuration.startMaximized = true;
    }

    @Test
    void successfulFillFormTest() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = fakeValuesService.bothify("????##@gmail.com");
        String userNumber = fakeValuesService.regexify("[0-9]{10}");
        String currentAddress = faker.address().fullAddress();

        String  gender = "Female",
                dayOfBirth = "02",
                monthOfBirth = "June",
                yearOfBirth = "1914",
                subject1Prefix = "i",
                subject1 = "History",
                subject2Prefix = "i",
                subject2 = "English",
                subject3Prefix = "s",
                subject3 = "Arts",
                hobbie1 = "Reading",
                hobbie2 = "Music",
                picture = "1.jpg",
                state = "Rajasthan",
                city = "Jaiselmer";

        open("https://demoqa.com/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userEmail").val(userEmail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").val(userNumber);
        //set date
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(monthOfBirth);
        $(".react-datepicker__year-select").selectOption(yearOfBirth);
        $(".react-datepicker__day--0" + dayOfBirth).click();
        //set subjects
        $("#subjectsInput").val(subject1Prefix);
        $(".subjects-auto-complete__menu-list").$(byText(subject1)).click();
        $("#subjectsInput").val(subject2Prefix);
        $(".subjects-auto-complete__menu-list").$(byText(subject2)).click();
        $("#subjectsInput").val(subject3Prefix);
        $(".subjects-auto-complete__menu-list").$(byText(subject3)).click();
        //set hobbies
        $("#hobbiesWrapper").$(byText(hobbie1)).click();
        $("#hobbiesWrapper").$(byText(hobbie2)).click();

        $("#uploadPicture").uploadFile(new File("src/test/resources/" + picture));

        $("#currentAddress").val(currentAddress);
        //set state and city
        $("#state").click();
        $("#stateCity-wrapper").$(byText(state)).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText(city)).click();

        $("#submit").click();

        //asserts
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        //$(".table-responsive").shouldHave(text(firstName + " " + lastName),
                //text(email), text (gender));
        //тоже самое, что и сверху
        $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
        $x("//td[text()='Student Email']").parent().shouldHave(text(userEmail));
        $x("//td[text()='Gender']").parent().shouldHave(text(gender));
        $x("//td[text()='Mobile']").parent().shouldHave(text(userNumber));
        $x("//td[text()='Date of Birth']").parent().shouldHave(text(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
        $x("//td[text()='Subjects']").parent().shouldHave(text(subject1 + ", " + subject2 + ", " + subject3));
        $x("//td[text()='Hobbies']").parent().shouldHave(text(hobbie1 + ", " + hobbie2));
        $x("//td[text()='Picture']").parent().shouldHave(text(picture));
        $x("//td[text()='Address']").parent().shouldHave(text(currentAddress));
        $x("//td[text()='State and City']").parent().shouldHave(text(state + " " + city));
    }
}