#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Apr  4 14:30:21 2020

@author: joan
"""

from skimage.io import imread, imsave
import matplotlib.pyplot as plt

# coordinates javascript and Pinta, first=column, second=row,
# reverse than numpy
crop = {"D1": [[ 142, 291], [ 257, 342]],
        "D2": [[ 262, 180], [ 301, 223]],
        "D3": [[ 610, 282], [ 708, 345]],
        "D4": [[ 698, 172], [ 734, 217]],
        "D5": [[ 602, 175], [ 639, 223]],
        "D6": [[ 646,  99], [ 698, 136]],
        "D7": [[1129, 170], [1164, 220]],
        "D8": [[1048, 172], [1083, 218]],
        "D9": [[1156, 101], [1191, 141]],
       }

plt.close('all')

for suffix in ["open", "closed"]:
    ima = imread("blueprint_"+suffix+".png")
    for k in crop.keys():
        [[y0,x0],[y1,x1]] = crop[k]
        c = ima[x0:x1,y0:y1,:]
        plt.figure()
        plt.imshow(c)
        plt.title(k)
        plt.imsave(k+"_"+suffix+".png", c)
        
        
    
    

