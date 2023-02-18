package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions) throws  Exception{
        //add an image to the blog
        if(!blogRepository2.findById(blogId).isPresent()){
            throw  new Exception();
        }

       Blog blog= blogRepository2.findById(blogId).get();
        Image image= new Image(blogId,description,dimensions);
        blog.getImageList().add(image);
        blogRepository2.save(blog);// As we save parent (Blog) child (image) will save automatically
        return image;
    }

    public void deleteImage(Integer id){
       imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) throws Exception {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        String screenArray[]=screenDimensions.split("x");
        if(!imageRepository2.findById(id).isPresent())
            throw  new Exception();

        Image image=imageRepository2.findById(id).get();
        String imageDimension= image.getDimensions();
        String imageArray[]=imageDimension.split("x");

        int screenLength=Integer.parseInt(screenArray[0]);
        int screenWidth=Integer.parseInt(screenArray[1]);

        int imageLength=Integer.parseInt(imageArray[0]);
        int imageWidth=Integer.parseInt(imageArray[1]);

        int length=screenLength/imageLength;
        int width=screenWidth/imageWidth;

        return length*width;
    }
}
