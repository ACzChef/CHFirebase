package com.aczchef.chfirebase.core;

import com.firebase.client.Firebase;
import com.laytonsmith.PureUtilities.Common.FileUtil;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.TermColors;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Cgallarno
 */
public class CHFirebaseAuth {
//    {
//        "firebase": "brothercraft.firebaseio.com",
//        "server_email": "user@email.com",
//        "server_password": "super secret"
//    }
    private static Firebase ref;
    private static String email;
    private static String passwd;

    public static String getEmail() {
        return email;
    }

    public static String getPasswd() {
        return passwd;
    }

    public static Firebase getRef() {
        return ref;
    }

    public static void setEmail(String email) {
        CHFirebaseAuth.email = email;
    }

    public static void setPasswd(String passwd) {
        CHFirebaseAuth.passwd = passwd;
    }

    public static void setRef(String ref) {
        if (!ref.startsWith("https://")) {
            ref = "https://" + ref;
        }
        CHFirebaseAuth.ref = new Firebase(ref);
    }
    
    public static void readAuth() throws URISyntaxException, IOException {
        File file;
        URL url = CHFirebase.class.getResource("/" + CHFirebase.class.getName().replace(".", "/") + ".class");
        String s = url.toString();
	s = s.replaceFirst("jar:file:", "");
	s = StringUtils.replaceLast(s, Pattern.quote(CHFirebase.class.getName().replace(".", "/") + ".class"), "");
        file = new File(s + "../CHFirebase.json");
        file = new File(file.getCanonicalPath());
        System.out.println(file.getCanonicalPath());
        if (file.createNewFile()) {
            FileUtil.write(
            "{\n" +
            "     \"firebase\": \"https://firebase.firebaseio.com\",\n" +
            "     \"server_email\": \"user@email.com\",\n" +
            "     \"server_password\": \"super secret\"\n" +
            "}", file);
            System.out.println("[CommandHelper] CHFirebase: Created config file - ACzChef");
        }
        String json = FileUtil.read(file);
        Object jsonv = JSONValue.parse(json);
        StringBuilder invalidConfig = new StringBuilder();
        invalidConfig.append(TermColors.RED).append("Compile Error").append(TermColors.WHITE).append(": Invalid CHfirebase Config.\n\t:Delete ").append(TermColors.YELLOW).append(file.getCanonicalPath()).append(TermColors.WHITE).append(" for a default config.");
        if (jsonv instanceof JSONObject) {
            JSONObject obj = (JSONObject) jsonv;
            if (obj.containsKey("firebase") && obj.containsKey("server_email") && obj.containsKey("server_password")) {
                setRef((String) obj.get("firebase"));
                setEmail((String) obj.get("server_email"));
                setPasswd((String) obj.get("server_password"));
            } else {
                System.out.println(invalidConfig.toString() + TermColors.reset());
            }
        } else {
            System.out.println(invalidConfig.toString() + TermColors.reset());
        }
        
    }
    
}
