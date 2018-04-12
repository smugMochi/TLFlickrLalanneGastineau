package centralesupelec.lalanne_ale.tlflickrlalannegastineau;

/**
 * Created by lalanne_ale on 27/03/18.
 */

public class MyImage {
    private String titre;
    private String url;

    public MyImage(String t, String u){
        this.titre = t;
        this.url = u;
    }

    public String getTitle(){ //  notions de GLOG :^)
        return this.titre;
    }

    public String getUrl(){ //  notions de GLOG :^)
        return this.url;
    }

}
