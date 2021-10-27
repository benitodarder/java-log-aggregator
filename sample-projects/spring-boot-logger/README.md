# spring-boot-modifiers

A simple aplication to showcase filters, webfilters, handler interceptors...

* Filter detected and in use with no further configuration. Executed second.
* Webfilter requires specifying the URL pattern to match and @ServletComponentScan annotation in the application class. Executed first.
* Handle interceptors require registering with one implementation of WebMvcConfigurer. Executed third.

