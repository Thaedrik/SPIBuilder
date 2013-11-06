SPIBuilder
==========

If you ever tried to develop a project with __SPI__ contributions on Eclipse,
you certainly have noticed when you launched your project that Eclipse
gently ignored your *services* declared in the `META-INF`.

Then, this plugin is made for you.

How it works
============

The problem is because of Eclipse which does not put your `META-INF` into the
`bin` folder.

This plugin, __SPIBuilder__, will automatically put the `META-INF` into the `bin` folder
whenever your project is *re-built*.

How to use
==========

Add the SPI Builder to your project declaring the SPI services :

1. Right click on the __project__;
2. In sub-menu __Configure__;
3. Click on __Toggle SPI Builder__.

That's it !

You can remove the SPI Builder by doing the same operation, the `META-INF` should be
removed from the `bin` folder on the *next build* or *clean*.

How to install
==============

The __SPIBuilder__ is installable from the __Codestorming update site__
at http://updatesite.codestorming.org/
