package jp.co.ridi.teldir.constant;

public enum Message {

    /** {0}を入力してください */
    E_CO_0001("e.co.0001"),
    /** {0}は{1}桁から{2}桁で入力してください */
    E_CO_0002("e.co.0001"),
    /** {0}は{1}形式で入力してください */
    E_CO_0003("e.co.0001"),

    /** IDが間違っています(相関チェック)。 */
    E_GC_GC001_0001("e.gc.gc001.0001"),

    /** 処理が正常に完了しました。 */
    I_GC_GC001_0001("i.gc.gc001.0001");

    // メッセージキー
    private String key;

    /**
     * コンストラクタ
     *
     * @param key
     *            メッセージキー
     */
    Message(String key) {
        this.key = key;
    }

    /**
     * メッセージキー取得
     *
     * @return メッセージキー
     */
    public String getKey() {
        return this.key;
    }

}
