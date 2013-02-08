lift_squeryl_demo
=================

A project demonstrating some simple Lift and Sqeuryl.

This project uses a pom.xml file that should work well as a basis for 
maven-driven lift projects that want to use all of eclipse's web
tools platform features (including the servers tab)

This pom.xml file works perfectly at the command line, and if you
have the proper eclipse plugins it will import perfectly with
the scala IDE, and will allow the web tools platform server support (I have
only tried Tomcat YMMV), JRebel reloading of classes on save, etc.

I use this basic template to do full-blown Liftweb developement,
and am quite pleased with it. You never need a command line,
can debug right from eclipse without complications, and get 
super-fast code-edit-compile-try cycles.

My biggest complaint is that it does seem to slow down over time,
so occassional restarts are necessary.

Usage with eclipse: 

I suggest eclipse.ini to include more RAM in the JVM args (the items below are
good if you have 4GB or more):

   -XX:MaxPermSize=512m

   -Xss2M

   -Xms1024m

   -Xmx2048m

Add these after the -vmargs line.

The basic list of things to install:

- Install Eclipse Indigo for J2EE (Juno works, but is quite a bit slower at the moment)
- Install scala IDE (scala-ide.org). I'm using 2.1 Milestone 3 with Indigo 
  as of 1/20/2013
- Install eclipse extras:
  - m2e (maven support) (from marketplace)
  - m2e-wtp (eclipse/maven/wtp support) (from marketplace)
  - m2e-scala http://alchim31.free.fr/m2e-scala/update-site/
  - JRebel Scala license (free)
  - JRebel eclipse plugin (marketplace)
  - JRebel WTP Integration (Yoxos eclipse marketplace)
  - JRebel m2eclipse Integration (Yoxos eclipse marketplace)
- Import your project (or this template project) as an Existing Maven Project.
- Open the Servers view
- Add a tomcat 6 server
- Double-click on the server, and check: Jrebel agent, serve without publishing, and never publish automatically.
- Right-click on the tomcat server, add Add/Remove Projects
- Add your webapp
- Press Run or Debug in the Servers tab
- Go to http://localhost:8080/nameofyourapp
- Edit, Save, Reload!
