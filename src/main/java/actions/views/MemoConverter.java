package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Memo;

/**
 * メモデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MemoConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param mv MemoViewのインスタンス
     * @return Memoのインスタンス
     */
    public static Memo toModel(MemoView mv) {
        return new Memo(
                mv.getId(),
                UserConverter.toModel(mv.getUser()),
                mv.getReportDate(),
                mv.getTitle(),
                mv.getContent(),
                mv.getCreatedAt(),
                mv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param m Memoのインスタンス
     * @return MemoViewのインスタンス
     */
    public static MemoView toView(Memo m) {

        if (m == null) {
            return null;
        }

        return new MemoView(
                m.getId(),
                UserConverter.toView(m.getUser()),
                m.getReportDate(),
                m.getTitle(),
                m.getContent(),
                m.getCreatedAt(),
                m.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MemoView> toViewList(List<Memo> list) {
        List<MemoView> uvs = new ArrayList<>();

        for (Memo m : list) {
            uvs.add(toView(m));
        }

        return uvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Memo m, MemoView mv) {
        m.setId(mv.getId());
        m.setUser(UserConverter.toModel(mv.getUser()));
        m.setReportDate(mv.getReportDate());
        m.setTitle(mv.getTitle());
        m.setContent(mv.getContent());
        m.setCreatedAt(mv.getCreatedAt());
        m.setUpdatedAt(mv.getUpdatedAt());

    }


    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void deleteViewToModel(Memo m) {
        m.setId(null);
        m.setUser(null);
        m.setReportDate(null);
        m.setTitle(null);
        m.setContent(null);
        m.setCreatedAt(null);
        m.setUpdatedAt(null);

    }
}