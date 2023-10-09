package guru.qa.models.user;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserListResponseModel {
    int page, per_page, total, total_pages;
    ArrayList<UserModel> data;
    SupportModel support;
}
