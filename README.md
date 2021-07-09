# FMI TrackMate Addons

[![Build Status](https://github.com/fmi-faim/fmi-trackmate-addons/actions/workflows/build-main.yml/badge.svg)](https://github.com/fmi-faim/fmi-trackmate-addons/actions/workflows/build-main.yml)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fb6f52f7465f4394b4128c8f1fb4f0d3)](https://www.codacy.com/app/imagejan/fmi-trackmate-addons?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fmi-faim/fmi-trackmate-addons&amp;utm_campaign=Badge_Grade)

These addons for [TrackMate](https://imagej.net/TrackMate) include:

* *Max Quality Spot Analyzer*: adds a spot feature `HAS_MAX_QUALITY_IN_FRAME` that is `1` if the spot has the highest quality of all spots in this frame, `0` otherwise
* Manual Features: these features are intended to be manipulated by scripts
  * *Custom Integer Spot Feature*
  * *Custom Double Spot Feature*
  * *Custom Integer Track Feature*
  * *Custom Double Track Feature*
* *Point-cloud registration tracker (old)*: a spot-linking algorithm based on the *Descriptor-based registration* plugin and the `mpicbg` library in Fiji (performing a global descriptor matching over all the spots of a pair of frames). *This tracker might ignore too many outliers and therefore not detect some tracks.*
* *Descriptor-based tracker*: a spot-linking algorithm based on the *point descriptors*, i.e. for each spot, we compare the distances to its n nearest neighbors, and compute the *descriptor distance* between two spots by comparing those n distances for both spots.
