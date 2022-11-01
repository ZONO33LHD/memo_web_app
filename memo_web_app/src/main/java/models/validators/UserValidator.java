package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 * ユーザーインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class UserValidator {

    /**
     * ユーザーインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param uv UserViewのインスタンス
     * @param codeDuplicateCheckFlag ユーザー番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            UserService service, UserView uv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //ユーザー番号のチェック
        String codeError = validateCode(service, uv.getCode(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

        //氏名のチェック
        String nameError = validateName(uv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passError = validatePassword(uv.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    /**
     * ユーザー番号の入力チェックを行い、エラーメッセージを返却
     * @param service UserServiceのインスタンス
     * @param code ユーザー番号
     * @param codeDuplicateCheckFlag ユーザー番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateCode(UserService service, String code, Boolean codeDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (code == null || code.equals("")) {
            return MessageConst.E_NOUSR_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {
            //ユーザー番号の重複チェックを実施

            long usersCount = isDuplicateUser(service, code);

            //同一ユーザー番号が既に登録されている場合はエラーメッセージを返却
            if (usersCount > 0) {
                return MessageConst.E_USR_CODE_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service UserServiceのインスタンス
     * @param code ユーザー番号
     * @return ユーザーテーブルに登録されている同一ユーザー番号のデータの件数
     */
    private static long isDuplicateUser(UserService service, String code) {

        long usersCount = service.countByCode(code);
        return usersCount;
    }

    /**
     * 氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}
