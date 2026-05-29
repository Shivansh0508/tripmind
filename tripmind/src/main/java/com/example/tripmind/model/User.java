package com.example.tripmind.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id private String id;
    private String name;
    @Indexed(unique = true) private String email;
    private String password;
    private List<String> savedTripIds = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();
}
