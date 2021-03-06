# Motivation

I often stay in hostels and have to pull out my wallet to pay for things (e.g. renting a bike, doing laundry, staying one more night, buying food ..) more often than I want.

The idea is that you have the ethereum address of an ho(s)tel anyway after booking with [winding-tree](https://windingtree.com) (not currently part of the API - but as far as I understood, by talking to the team, this is coming in the future) - so I can just pull out my phone and quickly sign a transaction instead of having to bother with clumsy old school payment methods. In order of having better UX, more privacy and less transaction costs I want to leverage off chain transactions for this (I also wanted to use the chance to get familiar with the [liquidity.network](https://liquidity.network) as they are present and mentoring at the hackathon where this project was initiated)

The start of the project is an Android app that displays different options for accommodation supplements and depending on what the user chooses shows a [liquidity.network](https://liquidity.network) QR-Code so the user can pay for it quickly.

Ideally at some point accommodation supplements (btw.: if someone has a better name for this please shoot) are part of the [winding-tree](https://windingtree.com) API. This way you might not even have to use a QR-Code but could directly do it from a booking app (which currently makes no sense to create as the search and booking API is not yet present). 

# Usage

Currently it is only running on an emulator as it wants to connect to the local liquidity network automator. This restriction will drop with a next version of the liquidity network where the automator is not required anymore.
So start the automator as described here: https://liquidity-sdk.readthedocs.io/en/latest/guide/index.html
Then start the app in the emulator (on the same computer) 
In the app you can click on the supplement you want and then you can scan the QR-code on the right side to pay for it with the liquidity wallet (initially I wanted to add liquidity.network support in [WallETH](https://walleth.org) - unfortunately this is currently prevented by the liquidity.network - but this will change in the future as [@thibmeu](https://github.com/thibmeu) from the liquidity.network told me while mentoring.
The idea is that this app could be installed in the ho(s)tel on a tablet mounted somewhere.

# Pictures credits

 * http://www.publicdomainfiles.com/show_file.php?id=13493565617304
 * http://pngimg.com/download/15576
 * https://www.goodfreephotos.com/vector-images/burrito-vector.png.php
 * https://pixabay.com/en/bed-metal-motel-pillow-gray-hotel-310382
 * https://www.goodfreephotos.com/vector-images/dumbbells-vector-clipart.png.php
 
# Context

 * https://github.com/windingtree/wt-hackathon/issues/10
 * https://github.com/windingtree/wt-hackathon/issues/4
 * https://windingtree.com/winding-tree-hackathon-prague-2018

# License

 GPLv3