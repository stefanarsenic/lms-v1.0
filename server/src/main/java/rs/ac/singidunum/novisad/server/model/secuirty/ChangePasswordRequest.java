package rs.ac.singidunum.novisad.server.model.secuirty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    private String username;
    private String currentPassword;
    private String newPassword;


}
