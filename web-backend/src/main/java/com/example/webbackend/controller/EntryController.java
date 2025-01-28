package com.example.webbackend.controller;

import com.example.webbackend.jwt.JWTUtil;
import com.example.webbackend.repository.PersonRepository;
import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.dtos.StringObjectMapDto;
import com.example.webbackend.web.BaseResponse;
import com.example.webbackend.web.ResponseHeader;
import com.example.webbackend.controller.services.PersonService;
import com.example.webbackend.repository.entity.enums.PersonType;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntryController {

    private final PersonRepository personRepository;
    private final PersonService personService;

    // بدون PasswordEncoder و AuthenticationManager
    public EntryController(PersonRepository personRepository,
                           PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    //==================== signin (بدون رمزنگاری) ====================
    @PostMapping("/signin")
    public BaseResponse<StringObjectMapDto> signin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            // جستجوی کاربر بر اساس نام کاربری
            Person person = personRepository.findByUsername(username)
                    .orElse(null);

            if (person == null) {
                // نام کاربری یافت نشد
                return new BaseResponse<>(ResponseHeader.USERNAME_NOT_EXISTS, null);
            }

            // مقایسه مستقیم رمز عبور (به صورت ساده)
            if (!person.getPassword().equals(password)) {
                // رمز نادرست
                return new BaseResponse<>(ResponseHeader.WRONG_PASSWORD, null);
            }

            // اگر درست بود، JWT بسازید
            String jwt = JWTUtil.generateToken(username);

            // **چاپ توکن در کنسول** برای Debug


            // ساختن dto برای فرستادن اطلاعات به فرانت
            StringObjectMapDto dto = new StringObjectMapDto();
            dto.put("token", jwt);
            dto.put("username", username);
            // اگر خواستید id یا چیز دیگری هم بگذارید
            dto.put("id", person.getId());
            System.out.println("SignIn - Generated token = " + dto.getData());

            return new BaseResponse<>(ResponseHeader.OK, dto);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(ResponseHeader.ERROR, null);
        }
    }

    //==================== signup (بدون رمزنگاری) ====================
    @PostMapping("/signup")
    public BaseResponse<StringObjectMapDto> signup(
            @RequestParam String username,
            @RequestParam String password
    ) {
        // بررسی اینکه کاربر از قبل وجود نداشته باشد
        if (personRepository.findByUsername(username).isPresent()) {
            // بهتر است از یک فیلد جداگانه مثلاً USERNAME_ALREADY_EXISTS استفاده کنید
            return new BaseResponse<>(ResponseHeader.USERNAME_NOT_EXISTS, null);
        }

        // ساختن شخص جدید، ذخیره پسورد به شکل ساده
        Person person = new Person();
        person.setUsername(username);
        person.setPassword(password); // بدون هش

        // اگر لازم دارید نقش بدهید
        person.setPersonType(PersonType.PLAYER);

        // اگر لازم دارید امتیاز = 0 تا بعدها NPE نشود
        person.setScore(0);

        // ذخیره در دیتابیس
        personRepository.save(person);

        // پاسخ موفق
        StringObjectMapDto dto = new StringObjectMapDto();
        dto.put("info", "User created successfully (no password hashing)!");

        return new BaseResponse<>(ResponseHeader.OK, dto);
    }
}
