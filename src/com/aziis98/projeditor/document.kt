package com.aziis98.projeditor

import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.util.*


// Copyright 2016 Antonio De Lucreziis

fun parseAttributes(box: Box, node: Node) {
    node.attributes.forEach { child ->
        box.attributes[child.nodeName] = child.nodeValue
    }
}

fun parseNode(parent: Box, node: Node) {
    if (node.nodeType != Node.ELEMENT_NODE) return

    val box = Box(node.nodeName, parent)

    parseAttributes(box, node)

    parent.children.add(box)

    node.childNodes.forEach { child ->
        parseNode(box, child)
    }
}

fun parseXML(doc: Document): BoxDocument {
    val root = Box(doc.documentElement.nodeName)

    parseAttributes(root, doc.documentElement)

    doc.documentElement.childNodes.forEach { child ->
        parseNode(root, child)
    }

    return BoxDocument(root)
}

fun NamedNodeMap.forEach(action: (Node) -> Unit) {
    for (i in 0 .. this.length - 1) {
        action.invoke(this.item(i))
    }
}

fun NodeList.forEach(action: (Node) -> Unit) {
    for (i in 0 .. this.length - 1) {
        action.invoke(this.item(i))
    }
}

class BoxDocument(val root: Box) {

    fun render(g: Graphics2D) {
        root.render(g)
    }

    fun update() {
        root.update()
    }

    fun querySelector(path: String) {

    }

}

class Box(val tagName: String, val parent: Box? = null) {

    val attributes: PropertyMap = PropertyMap()
    val children: ArrayList<Box> = ArrayList()

    fun render(g: Graphics2D) {

        g.color = Color.decode(attributes.get("fill-color", "0xFFFFFF"))
        g.fillRect(attributes.get("*x", 0), attributes.get("*y", 0), attributes.get("*width", 0), attributes.get("*height", 0))

        g.color = Color.decode(attributes.get("border-color", "0x000000"))
        g.stroke = BasicStroke(java.lang.Float.parseFloat(attributes.get("border-width", "1.0")))
        g.drawRect(attributes.get("*x", 0), attributes.get("*y", 0), attributes.get("*width", 0), attributes.get("*height", 0))

        for (child in children) {
            child.render(g)
        }
    }

    fun update() {
        updateLayout()

        for (child in children) {
            child.update()
        }
    }

    private fun updateLayout() {
        var margin = java.lang.Float.parseFloat(attributes.get("margin", "0"))

        if (parent == null) {

            attributes["*width"] = PROJEDITOR.windowWidth - 2 * margin
            attributes["*height"] = PROJEDITOR.windowHeight - 2 * margin

            attributes["*x"] = 0 + margin
            attributes["*y"] = 0 + margin

            return
        }

        when (parent.attributes.get("layout", "horizontal")) {

            "horizontal" -> {
                attributes["*width"] = parent.attributes.get("*width", 0) / parent.children.size - margin * 2
                attributes["*height"] = attributes.get<Int?>("height") ?: parent.attributes.get("*height", 0) - margin * 2

                println(attributes.get<Int>("height"))

                attributes["*x"] = parent.attributes.get("*x", 0) + parent.children.indexOf(this) * (parent.attributes.get("*width", 0) / parent.children.size) + margin
                attributes["*y"] = parent.attributes.get("*y", 0) + 0 + margin
            }

            "vertical" -> {
                attributes["*width"] = attributes.get<Int?>("width") ?: parent.attributes.get("*width", 0) - 2 * margin
                attributes["*height"] = parent.attributes.get("*height", 0) / parent.children.size - 2 * margin

                attributes["*x"] = parent.attributes.get("*x", 0) + 0 + margin
                attributes["*y"] = parent.attributes.get("*y", 0) + parent.children.indexOf(this) * (parent.attributes.get("*height", 0) / parent.children.size) + margin
            }

        }
    }

    override fun toString(): String {
        return children.map { it.toString() } .joinToString(";\n", "Box('$tagName', $attributes) [\n", "]")
    }

}































