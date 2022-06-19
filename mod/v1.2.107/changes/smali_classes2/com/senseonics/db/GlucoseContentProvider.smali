.class public Lcom/senseonics/db/GlucoseContentProvider;
.super Landroid/content/ContentProvider;
.source "GlucoseContentProvider.java"


# instance fields
.field helper:Lcom/senseonics/db/SenseonicsDBHelper;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 9
    invoke-direct {p0}, Landroid/content/ContentProvider;-><init>()V

    return-void
.end method

.method private performDelete(Landroid/database/sqlite/SQLiteDatabase;)I
    .locals 2

    const/4 v0, 0x0

    const-string v1, "deletenotsupported"

    .line 50
    invoke-virtual {p1, v1, v0, v0}, Landroid/database/sqlite/SQLiteDatabase;->delete(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I

    move-result p1

    return p1
.end method


# virtual methods
.method public delete(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I
    .locals 0

    .line 45
    iget-object p1, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {p1}, Lcom/senseonics/db/SenseonicsDBHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object p1

    .line 46
    invoke-direct {p0, p1}, Lcom/senseonics/db/GlucoseContentProvider;->performDelete(Landroid/database/sqlite/SQLiteDatabase;)I

    move-result p1

    return p1
.end method

.method public getType(Landroid/net/Uri;)Ljava/lang/String;
    .locals 0

    const/4 p1, 0x0

    return-object p1
.end method

.method public insert(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    .locals 4

    .line 31
    iget-object v0, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {v0}, Lcom/senseonics/db/SenseonicsDBHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v0

    const/4 v1, 0x0

    const-string v2, "select count(*) from glucosereadings"

    .line 33
    invoke-virtual {v0, v2, v1}, Landroid/database/sqlite/SQLiteDatabase;->rawQuery(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v2

    .line 34
    invoke-interface {v2}, Landroid/database/Cursor;->moveToFirst()Z

    const/4 v3, 0x0

    .line 35
    invoke-interface {v2, v3}, Landroid/database/Cursor;->getInt(I)I

    move-result v2

    if-lez v2, :cond_0

    .line 36
    invoke-direct {p0, v0}, Lcom/senseonics/db/GlucoseContentProvider;->performDelete(Landroid/database/sqlite/SQLiteDatabase;)I

    :cond_0
    const-string v2, "glucosereadings"

    .line 39
    invoke-virtual {v0, v2, v1, p2}, Landroid/database/sqlite/SQLiteDatabase;->insert(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J

    move-result-wide v0

    .line 40
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, ""

    invoke-virtual {p2, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {p1, p2}, Landroid/net/Uri;->withAppendedPath(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object p1

    return-object p1
.end method

.method public onCreate()Z
    .locals 2

    .line 15
    new-instance v0, Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {p0}, Lcom/senseonics/db/GlucoseContentProvider;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/senseonics/db/SenseonicsDBHelper;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    const/4 v0, 0x1

    return v0
.end method

.method public query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    .locals 8

    .line 21
    iget-object p1, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {p1}, Lcom/senseonics/db/SenseonicsDBHelper;->getReadableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v0

    const-string v1, "glucosereadings"

    const/4 v5, 0x0

    const/4 v6, 0x0

    move-object v2, p2

    move-object v3, p3

    move-object v4, p4

    move-object v7, p5

    invoke-virtual/range {v0 .. v7}, Landroid/database/sqlite/SQLiteDatabase;->query(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object p1

    return-object p1
.end method

.method public update(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    .locals 1

    .line 55
    iget-object p1, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {p1}, Lcom/senseonics/db/SenseonicsDBHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object p1

    const-string v0, "glucosereadings"

    invoke-virtual {p1, v0, p2, p3, p4}, Landroid/database/sqlite/SQLiteDatabase;->update(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I

    move-result p1

    return p1
.end method
