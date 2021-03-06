/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.javafx.pgstub;

import com.sun.javafx.geom.Path2D;
import com.sun.javafx.sg.PGSVGPath;


public class StubSVGPath extends StubShape implements PGSVGPath {

    private Object platformPath;

    public static class SVGPathImpl extends Path2D {
        String content;
        int windingRule;

        public SVGPathImpl(String content, int windingRule) {
            this.content = content;
            this.windingRule = windingRule;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return " content=" + content + ", windingRule=" + windingRule;
        }
    }

    @Override
    public void setContent(Object platformPath) {
        this.platformPath = platformPath;
    }

    public Object getGeometry() {
        return this.platformPath;
    }

    public String toString() {
        return "SVGPath[" + platformPath + "]";
    }

    private boolean acceptsPath2dOnUpdate = false;

    @Override
    public boolean acceptsPath2dOnUpdate() {
        return acceptsPath2dOnUpdate;
    }

    public void setAcceptsPath2dOnUpdate(boolean acceptsPath2dOnUpdate) {
        this.acceptsPath2dOnUpdate = acceptsPath2dOnUpdate;
    }
}
