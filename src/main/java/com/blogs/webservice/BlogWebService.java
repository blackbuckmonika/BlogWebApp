package com.blogs.webservice;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogs.dto.Blog;
import com.blogs.model.BlogManager;
import com.blogs.pojo.BlogPojo;

@RestController
public class BlogWebService {

	@Autowired
    BlogManager blogManager;  //Service which will do all data retrieval/manipulation work

	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	public ResponseEntity<List<Blog>> getBlogs(@RequestParam(value="start",required=false, defaultValue ="0") int start,
			@RequestParam(value="noOfBlogs", required=false, defaultValue ="0") int noOfBlogs){
		List<Blog> bl = new ArrayList<Blog>();
		if(noOfBlogs==0){
			bl = blogManager.getBlogs(start);
		}else{
			bl = blogManager.getBlogs(start,noOfBlogs);
		}
		if(bl.isEmpty()){
            return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND

		}
        return new ResponseEntity<List<Blog>>(bl, HttpStatus.OK);
		
	}
	@RequestMapping(value="/blog/{id}",method = RequestMethod.GET)
	public ResponseEntity<Blog> getBlog(@PathVariable(value="id") int id){
		
		Blog b = blogManager.getBlogById(id);
		if(b == null){
			System.out.println("Blog with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<Blog>(b, HttpStatus.OK);

	}

	@RequestMapping(value="/addBlog",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Blog> addBlog(@RequestBody BlogPojo blogPojo){
		
		Blog b = blogManager.addBlog(blogPojo.getTitle(),blogPojo.getContent());
		System.out.println(b);
		if(b == null){
			return new ResponseEntity<Blog>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Blog>(b,HttpStatus.OK);

	}
	/*
	@RequestMapping(value = "/blog/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") int id, @RequestBody Blog blog) {
        System.out.println("Updating Blog " + id);
         
        Blog currentBlog = blogManager.getBlogById(id);
         
        if (currentBlog==null) {
            System.out.println("Blog with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
 
        currentBlog.setTitle(blog.getTitle());
        blogManager.updateBlog(currentBlog);
        return new ResponseEntity<Blog>(currentBlog, HttpStatus.OK);
    }
 	*/
    //------------------- Delete a Blog --------------------------------------------------------
     
    @RequestMapping(value = "/blog/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBlog(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Blog with id " + id);
 
        Blog blog = blogManager.getBlogById(id);
        if (blog == null) {
            System.out.println("Unable to delete. Blog with id " + id + " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
 
        boolean isSuccessful = blogManager.deleteBlogById(id);
        if (isSuccessful == false){
            return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }	
}
