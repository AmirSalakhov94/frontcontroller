package tech.itpark.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ManyUser {
    private List<User> users;
}
