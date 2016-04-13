package com.aziis98.projeditor

import com.aziis98.canvas.CanvasApp
import com.aziis98.canvas.CanvasApplication
import java.awt.Graphics2D
import javax.xml.parsers.DocumentBuilderFactory


// Copyright 2016 Antonio De Lucreziis

class Main : CanvasApp() {

    var boxDocument: BoxDocument = BoxDocument(Box("window"))

    override fun init() {

        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc = dBuilder.parse("resources/view.xml")

        doc.documentElement.normalize();

        boxDocument = parseXML(doc)

        // println(boxDocument.root.toString())
    }

    override fun render(g: Graphics2D) {
        boxDocument.render(g)
    }

    override fun update() {
        boxDocument.update()
    }

}

val PROJEDITOR = Main()

fun main(args: Array<String>) {
    CanvasApplication.launch(PROJEDITOR)
}