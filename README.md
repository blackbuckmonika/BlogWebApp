## BlogApp

###Setup Tomcat server to deploy application
* sudo apt-get install tomcat7

###Install Maven - used for building project
* sudo apt-get install maven 

###Deploy project 
* Go to current project directory
* sudo cp target/BlogApp.war /var/lib/tomcat7/webapps/.

###Dump database in mysql
* Install Mysql dump data in mysql
Mysql -uroot -p < blog_db.sql

###Get list of blogs:- 
 * Start<Optional Parameter> - Default value 0 - start offset of result
 * NoOfBlogs<optional parameter>- default value is 5 per page.

**Request:**
http://localhost:8080/BlogApp/blogs?start=3&noOfBlogs=2

**Response**

```
[
  {
    "id": 4,
    "title": "fjsdfkjsdkfsknfjksdnfnsdkfnkjdsnkjfn",
    "created": "2016-12-02",
    "paragraphs": null,
    "authors": null,
    "tags": null,
    "deleted": false
  },
  {
    "id": 5,
    "title": "title",
    "created": "2016-12-02",
    "paragraphs": null,
    "authors": null,
    "tags": null,
    "deleted": false
  }
]
```

###Get Blog by Id:

**Request**
http://localhost:8080/BlogApp/blog/20/

**Response**
```
{
  "id": 20,
  "title": "3 para blog final",
  "created": "2016-12-02",
  "paragraphs": [
    {
      "id": 36,
      "paragraphText": "content1 vontgsdfv3q mkjk3 ",
      "comments": [
        {
          "id": 7,
          "commentText": "first comment",
          "created": "2016-12-02",
          "deleted": false
        },
        {
          "id": 8,
          "commentText": "second commen",
          "created": "2016-12-02",
          "deleted": false
        }
      ],
      "deleted": false
    },
    {
      "id": 37,
      "paragraphText": " second para is here ",
      "comments": [
        {
          "id": 9,
          "commentText": "send para first comment",
          "created": "2016-12-02",
          "deleted": false
        }
      ],
      "deleted": false
    },
    {
      "id": 38,
      "paragraphText": "third para is here",
      "comments": [],
      "deleted": false
    }
  ],
  "authors": null,
  "tags": null,
  "deleted": false
}
```

###Add Blog:-
http://localhost:8080/BlogApp/addBlog/

**Request Body:-**
* title:- title of the blog
* content:- Paragraphs and comment of the blogs.
* All the paragraphs are separated by \n\n and comments on paragraph are separated by "^^".

```
{
"content":"content1 vontgsdfv3q mkjk3 ^^first comment^^second commen\n\n second para is here ^^send para first comment\n\nthird para is here",
"title":"3 para blog final"
}
```

**Successresponse:-(Return created object)**

```
{
  "id": 21,
  "title": "3 para blog final",
  "created": 1480701937170,
  "paragraphs": [
    {
      "id": 39,
      "paragraphText": "content1 vontgsdfv3q mkjk3 ",
      "comments": [
        {
          "id": 0,
          "commentText": "first comment",
          "created": 1480701937306,
          "deleted": false
        },
        {
          "id": 0,
          "commentText": "second commen",
          "created": 1480701937306,
          "deleted": false
        }
      ],
      "deleted": false
    },
    {
      "id": 40,
      "paragraphText": " second para is here ",
      "comments": [
        {
          "id": 0,
          "commentText": "send para first comment",
          "created": 1480701937362,
          "deleted": false
        }
      ],
      "deleted": false
    },
    {
      "id": 41,
      "paragraphText": "third para is here",
      "comments": [],
      "deleted": false
    }
  ],
  "authors": null,
  "tags": null,
  "deleted": false
}
```


