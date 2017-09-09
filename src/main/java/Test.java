import org.opencv.core.Mat;

/**
 * Created by Admin on 08.09.2017.
 */
public class Test {

    public static void main(String[] args) {


        /*Load image*/
        Mat mat = imread("C:\\Users\\Admin\\IdeaProjects\\petproj\\javacv\\src\\main\\resources\\Test1.jpg");

        /*Resize image*/
        int max = Math.max(mat.cols(),mat.rows());
        double scale = max/700;
        resize(mat,mat,new Size((int) (mat.cols()/scale),(int) (mat.rows()/scale)));

        /*Change color scheme*/
        cvtColor(mat,mat,COLOR_BGR2RGB);

        /*Create new new mat with Gaussian blur*/
        Mat mat2 = mat.clone();
        GaussianBlur(mat2,mat2,new Size(7,7),0);
        cvtColor(mat2,mat2,COLOR_RGB2HSV);

        /*Filter*/
        int[] b1 = new int[]{0,0,0};
        int[] b2 = new int[]{30,30,30};
        Mat minBlack1 = new Mat(b1);
        Mat maxBlack1 = new Mat(b2);
        Mat mask1 = mat2.clone();
        inRange(mat2,minBlack1,maxBlack1,mask1);

        int[] b3 = new int[]{10,10,10};
        int[] b4 = new int[]{100,100,100};
        Mat minBlack2 = new Mat(b3);
        Mat maxBlack2 = new Mat(b4);
        Mat mask2 = mat2.clone();
        inRange(mat2,minBlack2,maxBlack2,mask2);
        for (int i = 0; i < 1; i++) {
            GaussianBlur(mask2,mask2,new Size(3,3),1);
        }
        Mat maskWithCountur = mask2.clone();

        /*Summarize masks*/
        Mat finalMask = add(mask1,mask2).asMat();

/*
        Mat kernel = getStructuringElement(MORPH_ELLIPSE,new Size(15,15));
        Mat closed = new Mat();
        */
        Mat overlay = new Mat();
        MatVector countur = new MatVector();
        findContours(maskWithCountur,countur,RETR_LIST,CHAIN_APPROX_SIMPLE);

        Mat[] mats = new Mat[(int)countur.size()];

        for (long i = 0 ;i<countur.size() ;i++) {
            mats[(int)i] = countur.get(i);
        }

        double maxSize = 0;
        int maxIndex = 0;
        for (int i=0 ; i<mats.length;i++){
            double area = cvContourArea();
            if (area>maxSize){
                maxSize = area;
                maxIndex = i;
            }
        }

        cvtColor(maskWithCountur,maskWithCountur,COLOR_GRAY2BGR);

        for (long i =0 ;i<countur.size();i++) {
            drawContours(maskWithCountur,countur,maxIndex,new Scalar(3,0,255,0));
        }





        /*Display images*/
        /*imshow("test",mat);
        imshow("test1",mat2);
        imshow("test2",mask1);*/
        /*imshow("test3",mask2);*/
        /*imshow("test4",finalMask);
        imshow("test5",kernel);*/
        imshow("test6",maskWithCountur);


   /*     Mat mask= new Mat();
        cvInRange(newmat.arrayData(),new int[]{0,100,80},new Scalar(0,80,200),mask);
*/
        cvWaitKey();
    }


}
