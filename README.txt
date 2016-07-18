GoCook 
Marisa Yeung
A recipe viewer and organizer

Demo
	https://www.youtube.com/watch?v=XN_B4v0nIIw

Table of Contents
I. Brief Intro
II. Future improvements
III. Known issues
IV. JSON recipe template
V. Original proposal

I. Brief Intro
	This app fetches notes from your evernote account. If the notes are recipes clipped from the nytimes.com website, they will be listed. Select a recipe to view the details for easy reference while cooking.

II. Future improvements
	- Pull down to refresh
	- Load image preview and larger images on the detailed view
	- Add parsers for other popular recipe websites
	- Add search capability
	- Add notebook browsing capability
		- currently can only view all notes

III. Known issues
	- Images can not be loaded
		- I wrote an async task to do it, but there was an error I couldn't resolve
			- when I tried loading the  url it said file not found, but I could get to it in my browser
	- Crashes when no network is available


	
IV. JSON recipe template for hand creating recipies for testing purposes
	{
	  "title”: "",
	  "author": "",
	  "time": "",
	  "yield": "",
	  "description": "",
	  "notes": [""],
	  “ingredients": [
	    {
	    "ingredient": "",
	    "amount": "",
	    "unit": "",
	    "processing": "",
	    "note": ""
	    }
	  ],  
	  "steps": [""],
	  "images”: [""],
	  "meta": {
	    "date created": "",
	    "last sync": "",
	    "url": "",
	    "tags": [""]
	  }
	}

V. Original Proposal
	Evernote Recipe viewer
	
	The Evernote app is a very convenient way to save and organize cooking recipes. However, it is not optimized for using the recipes while cooking.

	The problem is that there is no way to view a note in read only mode. It is easy to accidentally edit a note while reading it on a mobile device, and also easy to make unintended changes that result in a loss of the original text. This feature has been a request of users for many years [1] but has yet to be implemented in the Evernote app itself.

	Another problem is that sometimes the formatting is not ideal. Long recipes require scrolling, which makes it easy to lose your place while cooking.

	I propose creating an app which uses the Evernote API to access notes, to organize and display recipes in an intuitive and helpful way during the cooking process. The recipes will be read only with the option to append text. There will be a way to select/schedule recipes and grab from the ingredients list to make a shopping list. There will be a step by step view of the instructions with large clear text and large clear buttons. 

	[1] https://discussion.evernote.com/topic/58454-how-to-make-a-note-read-only/