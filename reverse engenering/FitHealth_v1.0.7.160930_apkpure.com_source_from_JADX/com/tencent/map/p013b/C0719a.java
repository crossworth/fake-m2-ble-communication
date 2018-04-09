package com.tencent.map.p013b;

public final class C0719a {
    private static C0719a f2494a = null;

    static /* synthetic */ class C07181 {
        final /* synthetic */ C0719a f2493a;

        private C07181(C0719a c0719a) {
            this.f2493a = c0719a;
        }

        public boolean m2399a(String str, String str2) {
            int a = C0719a.m2400a(this.f2493a, str);
            if (str2.charAt(4) != C0753s.f2626a.charAt(((((a * 9) + 10) / 3) + 36) & 31)) {
                return false;
            }
            if (str2.charAt(7) != C0753s.f2626a.charAt((((a * 5) + 11) / 5) & 31)) {
                return false;
            }
            if (str2.charAt(12) != C0753s.f2626a.charAt((((a + 10) / 3) << 3) & 31)) {
                return false;
            }
            if (str2.charAt(14) != C0753s.f2626a.charAt((((a * 3) + 19) / 9) & 31)) {
                return false;
            }
            if (str2.charAt(19) != C0753s.f2626a.charAt((((a * 3) + 39) / 8) & 31)) {
                return false;
            }
            if (str2.charAt(21) != C0753s.f2626a.charAt((((a / 23) + 67) / 7) & 31)) {
                return false;
            }
            if (str2.charAt(26) != C0753s.f2626a.charAt(((((a + 23) / 6) + 3) * 7) & 31)) {
                return false;
            }
            int i = 0;
            for (a = 0; a < str.length(); a++) {
                i = C0753s.f2627b[(i ^ C0753s.m2488a(str.charAt(a))) & 255] ^ ((i >> 8) & 255);
            }
            if (str2.charAt(0) != C0753s.f2626a.charAt(i & 31)) {
                return false;
            }
            return str2.charAt(1) == C0753s.f2626a.charAt((i >> 5) & 31);
        }
    }

    private C0719a() {
    }

    static /* synthetic */ int m2400a(C0719a c0719a, String str) {
        int i = 0;
        int length = str.length();
        int i2 = 0;
        while (i < length) {
            i2 += C0753s.m2488a(str.charAt(i));
            i++;
        }
        return ((length << 7) + length) ^ i2;
    }

    public static synchronized C0719a m2401a() {
        C0719a c0719a;
        synchronized (C0719a.class) {
            if (f2494a == null) {
                f2494a = new C0719a();
            }
            c0719a = f2494a;
        }
        return c0719a;
    }

    public final boolean m2402a(java.lang.String r9, java.lang.String r10) {
        /* JADX: method processing error */
/*
Error: java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
	at java.util.ArrayList.get(ArrayList.java:429)
	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:84)
	at jadx.core.dex.visitors.ModVisitor.getArgsToFieldsMapping(ModVisitor.java:313)
	at jadx.core.dex.visitors.ModVisitor.processAnonymousConstructor(ModVisitor.java:258)
	at jadx.core.dex.visitors.ModVisitor.processInvoke(ModVisitor.java:235)
	at jadx.core.dex.visitors.ModVisitor.replaceStep(ModVisitor.java:83)
	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:68)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r7 = 27;
        r1 = 1;
        r0 = 0;
        r2 = com.tencent.map.p013b.C0753s.m2495a(r9);
        if (r2 != 0) goto L_0x000b;
    L_0x000a:
        return r0;
    L_0x000b:
        r2 = com.tencent.map.p013b.C0753s.m2496b(r10);
        if (r2 == 0) goto L_0x000a;
    L_0x0011:
        r2 = new com.tencent.map.b.a$1;
        r2.<init>();
        r2 = r2.m2399a(r9, r10);
        if (r2 == 0) goto L_0x000a;
    L_0x001c:
        r2 = r0;
        r3 = r0;
    L_0x001e:
        if (r2 >= r7) goto L_0x0037;
    L_0x0020:
        r4 = r3 >> 8;
        r4 = r4 & 255;
        r5 = com.tencent.map.p013b.C0753s.f2627b;
        r6 = r10.charAt(r2);
        r6 = com.tencent.map.p013b.C0753s.m2488a(r6);
        r3 = r3 ^ r6;
        r3 = r3 & 255;
        r3 = r5[r3];
        r3 = r3 ^ r4;
        r2 = r2 + 1;
        goto L_0x001e;
    L_0x0037:
        r2 = r3 & 31;
        r4 = r10.charAt(r7);
        r5 = com.tencent.map.p013b.C0753s.f2626a;
        r2 = r5.charAt(r2);
        if (r4 == r2) goto L_0x004a;
    L_0x0045:
        r2 = r0;
    L_0x0046:
        if (r2 == 0) goto L_0x000a;
    L_0x0048:
        r0 = r1;
        goto L_0x000a;
    L_0x004a:
        r2 = r3 >> 5;
        r2 = r2 & 31;
        r3 = 28;
        r3 = r10.charAt(r3);
        r4 = com.tencent.map.p013b.C0753s.f2626a;
        r2 = r4.charAt(r2);
        if (r3 == r2) goto L_0x005e;
    L_0x005c:
        r2 = r0;
        goto L_0x0046;
    L_0x005e:
        r2 = r1;
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.a.a(java.lang.String, java.lang.String):boolean");
    }
}
