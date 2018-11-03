.class public Lcom/senseonics/db/GlucoseContentProvider;
.super Landroid/content/ContentProvider;
.source "GlucoseContentProvider.java"


# instance fields
.field helper:Lcom/senseonics/db/SenseonicsDBHelper;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 9
    invoke-direct {p0}, Landroid/content/ContentProvider;-><init>()V

    return-void
.end method

.method private performDelete(Landroid/database/sqlite/SQLiteDatabase;)I
    .locals 2
    .param p1, "database"    # Landroid/database/sqlite/SQLiteDatabase;

    .prologue
    const/4 v1, 0x0

    .line 50
    const-string v0, "deletenotsupported"

    invoke-virtual {p1, v0, v1, v1}, Landroid/database/sqlite/SQLiteDatabase;->delete(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I

    move-result v0

    return v0
.end method


# virtual methods
.method public delete(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I
    .locals 2
    .param p1, "uri"    # Landroid/net/Uri;
    .param p2, "selection"    # Ljava/lang/String;
    .param p3, "selectionArgs"    # [Ljava/lang/String;

    .prologue
    .line 45
    iget-object v1, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {v1}, Lcom/senseonics/db/SenseonicsDBHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v0

    .line 46
    .local v0, "database":Landroid/database/sqlite/SQLiteDatabase;
    invoke-direct {p0, v0}, Lcom/senseonics/db/GlucoseContentProvider;->performDelete(Landroid/database/sqlite/SQLiteDatabase;)I

    move-result v1

    return v1
.end method

.method public getType(Landroid/net/Uri;)Ljava/lang/String;
    .locals 1
    .param p1, "uri"    # Landroid/net/Uri;

    .prologue
    .line 26
    const/4 v0, 0x0

    return-object v0
.end method

.method public insert(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    .locals 6
    .param p1, "uri"    # Landroid/net/Uri;
    .param p2, "values"    # Landroid/content/ContentValues;

    .prologue
    const/4 v5, 0x0

    .line 31
    iget-object v4, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {v4}, Lcom/senseonics/db/SenseonicsDBHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v1

    .line 33
    .local v1, "database":Landroid/database/sqlite/SQLiteDatabase;
    const-string v4, "select count(*) from glucosereadings"

    invoke-virtual {v1, v4, v5}, Landroid/database/sqlite/SQLiteDatabase;->rawQuery(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v0

    .line 34
    .local v0, "cursor":Landroid/database/Cursor;
    invoke-interface {v0}, Landroid/database/Cursor;->moveToFirst()Z

    .line 35
    const/4 v4, 0x0

    invoke-interface {v0, v4}, Landroid/database/Cursor;->getInt(I)I

    move-result v4

    if-lez v4, :cond_0

    .line 36
    invoke-direct {p0, v1}, Lcom/senseonics/db/GlucoseContentProvider;->performDelete(Landroid/database/sqlite/SQLiteDatabase;)I

    .line 39
    :cond_0
    const-string v4, "glucosereadings"

    invoke-virtual {v1, v4, v5, p2}, Landroid/database/sqlite/SQLiteDatabase;->insert(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J

    move-result-wide v2

    .line 40
    .local v2, "id":J
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, ""

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {p1, v4}, Landroid/net/Uri;->withAppendedPath(Landroid/net/Uri;Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v4

    return-object v4
.end method

.method public onCreate()Z
    .locals 2

    .prologue
    .line 15
    new-instance v0, Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {p0}, Lcom/senseonics/db/GlucoseContentProvider;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Lcom/senseonics/db/SenseonicsDBHelper;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    .line 16
    const/4 v0, 0x1

    return v0
.end method

.method public query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    .locals 8
    .param p1, "uri"    # Landroid/net/Uri;
    .param p2, "projection"    # [Ljava/lang/String;
    .param p3, "selection"    # Ljava/lang/String;
    .param p4, "selectionArgs"    # [Ljava/lang/String;
    .param p5, "sortOrder"    # Ljava/lang/String;

    .prologue
    const/4 v5, 0x0

    .line 21
    iget-object v0, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {v0}, Lcom/senseonics/db/SenseonicsDBHelper;->getReadableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v0

    const-string v1, "glucosereadings"

    move-object v2, p2

    move-object v3, p3

    move-object v4, p4

    move-object v6, v5

    move-object v7, p5

    invoke-virtual/range {v0 .. v7}, Landroid/database/sqlite/SQLiteDatabase;->query(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v0

    return-object v0
.end method

.method public update(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    .locals 2
    .param p1, "uri"    # Landroid/net/Uri;
    .param p2, "values"    # Landroid/content/ContentValues;
    .param p3, "selection"    # Ljava/lang/String;
    .param p4, "selectionArgs"    # [Ljava/lang/String;

    .prologue
    .line 55
    iget-object v0, p0, Lcom/senseonics/db/GlucoseContentProvider;->helper:Lcom/senseonics/db/SenseonicsDBHelper;

    invoke-virtual {v0}, Lcom/senseonics/db/SenseonicsDBHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v0

    const-string v1, "glucosereadings"

    invoke-virtual {v0, v1, p2, p3, p4}, Landroid/database/sqlite/SQLiteDatabase;->update(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I

    move-result v0

    return v0
.end method
