# BlogApp

Spring Security
- If you just add the spring-boot-security-starter dependency, 
  it generates the default user as user and generates the random password (and also logs it) each time
- By default, Spring security protects (enables security) all the URLs in the application. We need to whitelist certain URLs.
- Add logging level to DEBUG for org.springframework.security in application.properties.
- user/random generated password + all exposed URLs are secured by default
- This type of authentication is FORM-BASED Authentication (By defauly Spring provides). This authentication is mainly used for Web application and not for Restful APIs.
- When you try to access any endpoint, spring security prompts username & password for authentication. By default form based authentication
- Before serving the requests to client, spring security makes sure that user has been authenticated
- We can also configure username/password using spring security provided properties in application.properties file
- spring.security.user.name=admin
  spring.security.user.password=admin
  spring.security.user.roles=ADMIN
- Excerpt from log
  - w.c.HttpSessionSecurityContextRepository : Retrieved SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_ADMIN]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=E3E60CB9AFDD77998411F967268CD23C], Granted Authorities=[ROLE_ADMIN]]]
  - s.s.w.c.SecurityContextPersistenceFilter : Set SecurityContextHolder to SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[ROLE_ADMIN]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=E3E60CB9AFDD77998411F967268CD23C], Granted Authorities=[ROLE_ADMIN]]]
  - o.s.s.w.a.i.FilterSecurityInterceptor    : Authorized filter invocation [GET /api/posts] with attributes [authenticated]