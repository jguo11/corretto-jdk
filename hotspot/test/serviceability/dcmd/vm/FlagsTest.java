/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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

import com.oracle.java.testlibrary.OutputAnalyzer;
import com.oracle.java.testlibrary.dcmd.CommandExecutor;
import com.oracle.java.testlibrary.dcmd.JMXExecutor;
import org.testng.annotations.Test;

/*
 * @test
 * @summary Test of diagnostic command VM.flags
 * @library /testlibrary
 * @build com.oracle.java.testlibrary.*
 * @build com.oracle.java.testlibrary.dcmd.*
 * @run testng/othervm -Xmx129m -XX:+PrintGC -XX:+UnlockDiagnosticVMOptions -XX:+IgnoreUnrecognizedVMOptions -XX:+ThereShouldNotBeAnyVMOptionNamedLikeThis_Right -XX:-TieredCompilation FlagsTest
 */
public class FlagsTest {
    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("VM.flags");

        /* The following are interpreted by the JVM as actual "flags" */
        output.shouldContain("-XX:+PrintGC");
        output.shouldContain("-XX:+UnlockDiagnosticVMOptions");
        output.shouldContain("-XX:+IgnoreUnrecognizedVMOptions");
        output.shouldContain("-XX:-TieredCompilation");

        /* The following are not */
        output.shouldNotContain("-Xmx129m");
        output.shouldNotContain("-XX:+ThereShouldNotBeAnyVMOptionNamedLikeThis_Right");
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
