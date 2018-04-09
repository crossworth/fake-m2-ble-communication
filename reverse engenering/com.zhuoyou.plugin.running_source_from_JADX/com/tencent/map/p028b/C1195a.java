package com.tencent.map.p028b;

public final class C1195a {
    private static C1195a f3749a = null;

    static /* synthetic */ class C11941 {
        final /* synthetic */ C1195a f3748a;

        private C11941(C1195a c1195a) {
            this.f3748a = c1195a;
        }

        public boolean m3498a(String str, String str2) {
            int a = C1195a.m3499a(this.f3748a, str);
            if (str2.charAt(4) != C1223i.f3892a.charAt(((((a * 9) + 10) / 3) + 36) & 31)) {
                return false;
            }
            if (str2.charAt(7) != C1223i.f3892a.charAt((((a * 5) + 11) / 5) & 31)) {
                return false;
            }
            if (str2.charAt(12) != C1223i.f3892a.charAt((((a + 10) / 3) << 3) & 31)) {
                return false;
            }
            if (str2.charAt(14) != C1223i.f3892a.charAt((((a * 3) + 19) / 9) & 31)) {
                return false;
            }
            if (str2.charAt(19) != C1223i.f3892a.charAt((((a * 3) + 39) / 8) & 31)) {
                return false;
            }
            if (str2.charAt(21) != C1223i.f3892a.charAt((((a / 23) + 67) / 7) & 31)) {
                return false;
            }
            if (str2.charAt(26) != C1223i.f3892a.charAt(((((a + 23) / 6) + 3) * 7) & 31)) {
                return false;
            }
            int i = 0;
            for (a = 0; a < str.length(); a++) {
                i = C1223i.f3893b[(i ^ C1223i.m3621a(str.charAt(a))) & 255] ^ ((i >> 8) & 255);
            }
            if (str2.charAt(0) != C1223i.f3892a.charAt(i & 31)) {
                return false;
            }
            return str2.charAt(1) == C1223i.f3892a.charAt((i >> 5) & 31);
        }
    }

    private C1195a() {
    }

    static /* synthetic */ int m3499a(C1195a c1195a, String str) {
        int i = 0;
        int length = str.length();
        int i2 = 0;
        while (i < length) {
            i2 += C1223i.m3621a(str.charAt(i));
            i++;
        }
        return ((length << 7) + length) ^ i2;
    }

    public static synchronized C1195a m3500a() {
        C1195a c1195a;
        synchronized (C1195a.class) {
            if (f3749a == null) {
                f3749a = new C1195a();
            }
            c1195a = f3749a;
        }
        return c1195a;
    }

    public final boolean m3501a(java.lang.String r9, java.lang.String r10) {
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
        r2 = com.tencent.map.p028b.C1223i.m3628a(r9);
        if (r2 != 0) goto L_0x000b;
    L_0x000a:
        return r0;
    L_0x000b:
        r2 = com.tencent.map.p028b.C1223i.m3629b(r10);
        if (r2 == 0) goto L_0x000a;
    L_0x0011:
        r2 = new com.tencent.map.b.a$1;
        r2.<init>();
        r2 = r2.m3498a(r9, r10);
        if (r2 == 0) goto L_0x000a;
    L_0x001c:
        r2 = r0;
        r3 = r0;
    L_0x001e:
        if (r2 >= r7) goto L_0x0037;
    L_0x0020:
        r4 = r3 >> 8;
        r4 = r4 & 255;
        r5 = com.tencent.map.p028b.C1223i.f3893b;
        r6 = r10.charAt(r2);
        r6 = com.tencent.map.p028b.C1223i.m3621a(r6);
        r3 = r3 ^ r6;
        r3 = r3 & 255;
        r3 = r5[r3];
        r3 = r3 ^ r4;
        r2 = r2 + 1;
        goto L_0x001e;
    L_0x0037:
        r2 = r3 & 31;
        r4 = r10.charAt(r7);
        r5 = com.tencent.map.p028b.C1223i.f3892a;
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
        r4 = com.tencent.map.p028b.C1223i.f3892a;
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
